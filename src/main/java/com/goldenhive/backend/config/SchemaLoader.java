package com.goldenhive.backend.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchemaLoader {

    private static final String SCHEMA_LOCATION = "db/schema/";

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void loadSchemas() {
        try {
            ClassPathResource schemaDirectory = new ClassPathResource(SCHEMA_LOCATION);
            if (!schemaDirectory.exists()) {
                log.warn("Schema directory not found: {}", SCHEMA_LOCATION);
                return;
            }

            Resource[] resources = new PathMatchingResourcePatternResolver()
                    .getResources("classpath*:" + SCHEMA_LOCATION + "*.json");

            for (Resource resource : resources) {
                applySchema(resource);
            }

            createIndexes();
        } catch (Exception ex) {
            log.error("Failed to initialize MongoDB schemas", ex);
        }
    }

    private void applySchema(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            Document schemaDocument = Document.parse(json);
            String collectionName = schemaDocument.getString("collection");
            Document validator = schemaDocument.get("validator", Document.class);

            if (collectionName == null || validator == null) {
                log.warn("Skipping invalid schema file: {}", resource.getFilename());
                return;
            }

            if (collectionExists(collectionName)) {
                Document collModCommand = new Document("collMod", collectionName)
                        .append("validator", validator)
                        .append("validationLevel", "moderate");
                mongoTemplate.getDb().runCommand(collModCommand);
            } else {
                Document createCommand = new Document("create", collectionName)
                        .append("validator", validator)
                        .append("validationLevel", "moderate");
                mongoTemplate.getDb().runCommand(createCommand);
            }

            log.info("Schema loaded successfully for collection: {}", collectionName);
        } catch (Exception ex) {
            log.error("Failed to load schema from file: {}", resource.getFilename(), ex);
        }
    }

    private boolean collectionExists(String collectionName) {
        List<String> collections = mongoTemplate.getDb().listCollectionNames().into(new ArrayList<>());
        return collections.contains(collectionName);
    }

    private void createIndexes() {
        try {
            ensureIndexIfMissing("packages", new Document("slug", 1), new Index().on("slug", Sort.Direction.ASC).unique());
            ensureIndexIfMissing("bookings", new Document("packageId", 1), new Index().on("packageId", Sort.Direction.ASC));
            ensureIndexIfMissing("carts", new Document("packageId", 1), new Index().on("packageId", Sort.Direction.ASC));
            ensureIndexIfMissing("package_activity_mappings", new Document("packageId", 1), new Index().on("packageId", Sort.Direction.ASC));
            ensureIndexIfMissing("package_activity_mappings", new Document("activityId", 1), new Index().on("activityId", Sort.Direction.ASC));

            log.info("MongoDB indexes ensured successfully");
        } catch (Exception ex) {
            log.error("Failed to create MongoDB indexes", ex);
        }
    }

    private void ensureIndexIfMissing(String collectionName, Document expectedKey, Index index) {
        boolean indexExists = mongoTemplate.getCollection(collectionName)
                .listIndexes()
                .into(new ArrayList<>())
                .stream()
                .map(document -> document.get("key", Document.class))
                .anyMatch(expectedKey::equals);

        if (indexExists) {
            log.info("Skipping existing index for collection {} and key {}", collectionName, expectedKey.toJson());
            return;
        }

        mongoTemplate.indexOps(collectionName).ensureIndex(index);
        log.info("Created index for collection {} and key {}", collectionName, expectedKey.toJson());
    }
}

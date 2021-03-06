package org.kafkahq.models;

import lombok.*;
import org.apache.avro.Schema.Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
@Getter
public class Schema {
    private final Parser parser = new Parser();

    private final Integer id;
    private final String subject;
    private final Integer version;
    private final org.apache.avro.Schema schema;

    public Schema(io.confluent.kafka.schemaregistry.client.rest.entities.Schema schema) {
        this.id = schema.getId();
        this.subject = schema.getSubject();
        this.version = schema.getVersion();
        this.schema = parser.parse(schema.getSchema());
    }

    @ToString
    @EqualsAndHashCode
    @Getter
    @Builder
    public static class Config {
        public enum CompatibilityLevelConfig {
            NONE,
            BACKWARD,
            BACKWARD_TRANSITIVE,
            FORWARD,
            FORWARD_TRANSITIVE,
            FULL,
            FULL_TRANSITIVE
        }

        public static List<String> getCompatibilityLevelConfigList() {
            return new ArrayList<>(Arrays.asList(Schema.Config.CompatibilityLevelConfig
                .values()))
                .stream()
                .map(Enum::name)
                .collect(Collectors.toList());
        }

        private final CompatibilityLevelConfig compatibilityLevel;

        public Config(io.confluent.kafka.schemaregistry.client.rest.entities.Config config) {
            this.compatibilityLevel = CompatibilityLevelConfig.valueOf(config.getCompatibilityLevel());
        }

        public Config(CompatibilityLevelConfig config) {
            this.compatibilityLevel = config;
        }
    }
}

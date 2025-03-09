package bidgood.global.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidGeneratorImpl implements UuidGenerator {
    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }
}

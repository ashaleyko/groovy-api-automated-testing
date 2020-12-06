package config

import org.aeonbits.owner.ConfigFactory

class Config {

    private final EnvConfig envConfigVar = ConfigFactory.create(EnvConfig.class)

    static EnvConfig getEnvConfig() {
        return LazyHolder.INSTANCE.envConfigVar
    }

    private static class LazyHolder {

        private static final Config INSTANCE = new Config()

        private LazyHolder() {
        }
    }
}

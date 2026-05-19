import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

final class ClusterConfiguration {

    static final String SYSTEM_NAME = "PekkoBasicCluster";
    static final int GUARDIAN_PORT = 8888;

    private ClusterConfiguration () {}

    static Config create (int port) {
        String configuration = """
            pekko {
                actor {
                    provider = cluster
                    allow-java-serialization = on
                    warn-about-java-serializer-usage = off
                }

                remote.artery {
                    transport = tcp
                    canonical.hostname = "localhost"
                    canonical.port = %d
                }

                cluster.seed-nodes = [ "pekko://%s@localhost:%d" ]
            }
            """.formatted (port, SYSTEM_NAME, GUARDIAN_PORT);

        return ConfigFactory.parseString (configuration);
    }
}

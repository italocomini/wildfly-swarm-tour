package lifelog;

import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jpa.JPAFraction;

public class LifeLogConfiguration {

  private Swarm swarm;

  LifeLogConfiguration(Swarm swarm) {
    this.swarm = swarm;
  }

  DatasourcesFraction datasourcesFraction(String datasourceName) {
    return new DatasourcesFraction()
      .dataSource(datasourceName, ds -> ds
        .driverName(resolve("database.driver.name"))
        .connectionUrl(databaseConnectionUrl())
        .userName(resolve("database.userName"))
        .password(resolve("database.password"))
      );
  }

  JPAFraction jpaFraction(String datasourceName) {
    return new JPAFraction()
      .defaultDatasource("jboss/datasources/" + datasourceName);
  }

  private String databaseConnectionUrl() {
    String urlFromEnv = System.getenv("DB_PORT_5432_TCP_ADDR") + ":" + System.getenv("DB_PORT_5432_TCP_PORT");

    return urlFromEnv.equals(":") ?
      resolve("database.connection.url") :
      "jdbc:postgresql://" + urlFromEnv + "/lifelog";
  }

  private String resolve(String key) {
    return swarm.stageConfig().resolve(key).getValue();
  }

}

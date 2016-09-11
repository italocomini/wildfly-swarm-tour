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
      .jdbcDriver(resolve("database.driver.name"), d -> d
        .driverClassName(resolve("database.driver.className"))
        .xaDatasourceClass(resolve("database.driver.xaDatasourceClass"))
        .driverModuleName(resolve("database.driver.moduleName"))
      )
      .dataSource(datasourceName, ds -> ds
        .driverName(resolve("database.driver.name"))
        .connectionUrl(resolve("database.connection.url"))
        .userName(resolve("database.userName"))
        .password(resolve("database.password"))
      );
  }

  JPAFraction jpaFraction(String datasourceName) {
    return new JPAFraction()
      .defaultDatasource("jboss/datasources/" + datasourceName);
  }

  private String resolve(String key) {
    return swarm.stageConfig().resolve(key).getValue();
  }

}

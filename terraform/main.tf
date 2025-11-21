terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.2"
    }
  }

  required_version = ">= 1.5.0"
}

provider "docker" {}


resource "docker_network" "franquicias_net" {
  name = "franquicias-net"
}


resource "docker_volume" "db_data" {
  name = "franquicias-api_db_data"
}


resource "docker_image" "mysql" {
  name         = "mysql:8.0"
  keep_locally = false
}

resource "docker_container" "db" {
  name  = "franquicias-db"
  image = docker_image.mysql.image_id


  ports {
    internal = 3306
    external = 3307
  }

  env = [
    "MYSQL_ROOT_PASSWORD=root",
    "MYSQL_DATABASE=franquicias_db",
    "MYSQL_USER=root",
    "MYSQL_PASSWORD=root",
  ]

  mounts {
    target = "/var/lib/mysql"
    source = docker_volume.db_data.name
    type   = "volume"
  }

  networks_advanced {
    name = docker_network.franquicias_net.name
  }
}


resource "docker_image" "api" {
  name         = "franquicias-api-api:latest"
  keep_locally = true
}


resource "docker_container" "api" {
  name  = "franquicias-api"
  image = docker_image.api.image_id

  depends_on = [
    docker_container.db
  ]

  # Puertos: host 8081 -> container 8080
  ports {
    internal = 8080
    external = 8081
  }

  env = [
    # Conectamos a la BD por nombre de contenedor dentro de la red
    "SPRING_DATASOURCE_URL=jdbc:mysql://franquicias-db:3306/franquicias_db",
    "SPRING_DATASOURCE_USERNAME=root",
    "SPRING_DATASOURCE_PASSWORD=root",
    "SPRING_JPA_HIBERNATE_DDL_AUTO=update",
  ]

  networks_advanced {
    name = docker_network.franquicias_net.name
  }
}

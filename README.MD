# TerraDiscovery

TerraDiscovery est un plugin Bukkit pour le serveur PLAY.TerraCraft.FR qui propose un système innovant de découverte de zones en utilisant WorldGuardAPI. Le plugin permet de créer des zones de découverte avec des paramètres personnalisables tels que le temps d'attente avant la téléportation, les permissions et les coordonnées de téléportation.

## Caractéristiques

- Système innovant de découverte qui réinvente le /warp traditionnel en utilisant WorldGuardAPI : configuration française pour le serveur PLAY.TerraCraft.FR
- Pas besoin de base de données
- Nécessite le plugin WorldGuard Bukkit 7.0.9 minimum
- Totalement configurable :
    - Cooldown par warps avec permission
    - Commandes intuitives pour créer et supprimer une zone + définir le point d'apparition & voir la liste des warps avec leurs paramètres
    - Messages des joueurs configurables
    - Menu GUI qui affiche automatiquement les zones disponibles, avec les zones débloquées ou non avec item & nom différent + màj du menu automatique

## Permissions

- Permissions par warp personnalisables
- `terradiscoveryadmin.use` : accès aux commandes d'administration comme créer ou supprimer des zones
- `terradiscovery.warps` : accès au menu GUI des zones

## Commandes

- `/td reload` ou `/tda reload` : recharge la configuration du plugin
- `/td list` ou `/tda list` : affiche la liste des warps disponibles
- `/td create <nom-du-warp>` ou `/tda create <nom-du-warp>` : crée une nouvelle zone de découverte avec le nom spécifié
- `/td setspawn <nom-du-warp>` : défini le point d'apparition pour la zone spécifiée
- `/td remove <nom-du-warp>` : supprime complètement une zone de découverte et toutes ses sous-sections

## Aliases des commandes

- `warp`
- `warps`
- `zone`
- `zones`

## Configuration

Le fichier de configuration du plugin doit être placé dans le dossier `plugins/TerraDiscovery/`.

### Exemple de configuration

```yaml
messages:
  help:
    - "&6TerraDiscovery -- Aide"
    - "&aAlias : /td"
    - " "
    - "&7&l➤ &b/td reload &f: &7Recharger la configuration"
    - "&7&l➤ &b/td list &f: &7Voir la liste des zones à découvrir et voir leurs configurations"
    - " "
  warps:
    added:
      - "&7&l&7&l➤ &bVous avez créé le warp &7%warp_name% &b!"
    unlocked:
      - "&e&l⚡ &b&lPLAY.TERRACRAFT.FR &e&l⚡"
      - " "
      - "&e&l✪ &7&lNouvelle zone débloquée ! &e&l✪"
      - "&7➤ &bFélicitation ! Vous avez débloqué une nouvelle zone !"
      - "&7➤ &bVous pouvez y accéder grâce à /warps !"
      - "&a&oVisitez notre boutique /credits pour débloquer tous les warps"
      - " "
    alreadyExist: "&cErreur, ce warp existe déjà!"

titles:
  unlocked:
    firstLine: "&e&lNouvelle zone débloquée !"
    secondLine: "&fVous avez débloqué la zone : %warpDisplayName%"

warps:
  test:
    display-name: "&6Test"
    permission: warps.test
    region: test_rg # Nom de la région WorldGuard
    x: 0.0
    y: 64.0
    z: 0.0
    pitch: 0.0
    yaw: 0.0
    cooldown: 10 # Temps d'attente en secondes avant la téléportation
```
# TerraDiscovery ENGLISH

TerraDiscovery is a Bukkit plugin for the PLAY.TerraCraft.FR server that offers an innovative discovery system for zones using WorldGuardAPI. The plugin allows you to create discovery zones with customizable parameters such as teleportation cooldown, permissions, and teleportation coordinates.

## Features

- Innovative discovery system that reinvents the traditional /warp using WorldGuardAPI: French configuration for the PLAY.TerraCraft.FR server
- No need for a database
- Requires minimum WorldGuard Bukkit 7.0.9 plugin
- Fully configurable:
  - Cooldown per warps with permissions
  - Intuitive commands to create and remove a zone + set the spawn point & view the list of warps with their settings
  - Configurable player messages
  - GUI menu that automatically displays available zones, with unlocked or locked zones with different items and names + automatic menu updates

## Permissions

- Customizable permissions per warp
- `terradiscoveryadmin.use`: Access to administration commands such as creating or removing zones
- `terradiscovery.warps`: Access to the zones GUI menu

## Commands

- `/td reload` or `/tda reload`: Reloads the plugin's configuration
- `/td list` or `/tda list`: Shows the list of available warps
- `/td create <warp-name>` or `/tda create <warp-name>`: Creates a new discovery zone with the specified name
- `/td setspawn <warp-name>`: Sets the spawn point for the specified zone
- `/td remove <warp-name>`: Completely removes a discovery zone and all its subsections

## Command Aliases

- `warp`
- `warps`
- `zone`
- `zones`

## Configuration

The plugin's configuration file should be placed in the `plugins/TerraDiscovery/` folder.

### Example Configuration

```yaml
messages:
  help:
    - "&6TerraDiscovery -- Help"
    - "&aAlias: /td"
    - " "
    - "&7&l➤ &b/td reload &f: &7Reload the configuration"
    - "&7&l➤ &b/td list &f: &7View the list of discovery zones and their configurations"
    - " "
  warps:
    added:
      - "&7&l&7&l➤ &bYou have created the warp &7%warp_name% &b!"
    unlocked:
      - "&e&l⚡ &b&lPLAY.TERRACRAFT.FR &e&l⚡"
      - " "
      - "&e&l✪ &7&lNew zone unlocked! &e&l✪"
      - "&7➤ &bCongratulations! You have unlocked a new zone!"
      - "&7➤ &bYou can access it using /warps!"
      - "&a&oVisit our shop /credits to unlock all warps"
      - " "
    alreadyExist: "&cError, this warp already exists!"

titles:
  unlocked:
    firstLine: "&e&lNew zone unlocked!"
    secondLine: "&fYou have unlocked the zone: %warpDisplayName%"

warps:
  test:
    display-name: "&6Test"
    permission: warps.test
    region: test_rg # The WorldGuard region name
    x: 0.0
    y: 64.0
    z: 0.0
    pitch: 0.0
    yaw: 0.0
    cooldown: 10 # Teleportation cooldown in seconds

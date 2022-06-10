[![Build Status](https://jenkins.bennydoesstuff.me/buildStatus/icon?job=Scaffold)](https://jenkins.bennydoesstuff.me/job/Scaffold)

# Scaffold
Scaffold is a simple world management plugin, re-written in Kotlin. It provides mapmakers variety of commands to easily manage worlds on Minecraft 1.8 servers.

This project was originally based on a fork of Avicus Network's [Scaffold Build Plugin](https://github.com/Avicus/Scaffold) that was later updated for Minecraft 1.16.5 by Warzone contributors. Kudos to them for their great work.

## Server Setup

1. Start with the latest [SportPaper](https://github.com/Electroid/SportPaper) build from its release page or [here](https://pkg.ashcon.app/sportpaper).

2. Compile the latest version of Scaffold (`gradle shadowJar`) or download it from GitHub Actions or our [Jenkins](https://jenkins.bennydoesstuff.me/job/Scaffold/).

3. Start the server. The 3 most basic commands in the plugin are ``/create``, ``/worlds``, and ``/open``.

## Permissions
| Command       | Description   | Permission Node |
| ------------- | ------------- | ------------- |
| `/lock`  | Lock a world at this time.  | `scaffold.command.lock`  |
| `/archive [-k]`  | Archive and delete a world. Use `-k` to keep on server.  | `scaffold.command.archive`  |
| `/create <world name>`  | Create a new, empty world.  | `scaffold.command.create`  |
| `/open <world name>`  | Open a world.  | `scaffold.command.open`  |
| `/close <world name>`  | Close a world and remove all players in it.  | `scaffold.command.close`  |
| `/export <world name>`  | Export a world and provide a download link.  | `scaffold.command.export`  |
| `/import <link ending in .zip> <world name>`  | Import a world.  | `scaffold.command.import`  |
| `/worlds`  | Show all worlds.  | `scaffold.command.worlds`  |

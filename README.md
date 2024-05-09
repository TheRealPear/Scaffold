[![Build Status](https://jenkins.bennydoesstuff.me/buildStatus/icon?job=Scaffold)](https://jenkins.bennydoesstuff.me/job/Scaffold)

# Scaffold
Scaffold is a simple world management plugin for build servers. It provides mapmakers variety of commands to easily manage worlds on Minecraft 1.8 servers.

## Server Setup

1. Start with the latest [SportPaper](https://github.com/Electroid/SportPaper) build from its release page or [here](https://pkg.ashcon.app/sportpaper).
   - The server must use Java 8.

2. Compile the latest version of Scaffold with Maven (`mvn clean install`) or from [Jenkins](https://jenkins.bennydoesstuff.me/job/Scaffold/).

3. Start the server.
   - The three most basic commands available for players are ``/create``, ``/worlds``, and ``/open``.

## Commands & Permissions
| Command       | Description   | Permission Node |
| ------------- | ------------- | ------------- |
| `/lock <world>`  | Lock a world at this time.  | `scaffold.command.lock`  |
| `/archive [-k] <world>`  | Archive and delete a world. Use `-k` to keep on server.  | `scaffold.command.archive`  |
| `/create <world>`  | Create a new, empty world.  | `scaffold.command.create`  |
| `/open <world>`  | Open a world.  | `scaffold.command.open`  |
| `/close <world>`  | Close a world and remove all players in it.  | `scaffold.command.close`  |
| `/export <world>`  | Export a world and provide a download link.  | `scaffold.command.export`  |
| `/import <link ending in .zip> <world>`  | Import a world.<sup href>[[1]](#footnote1)</sup>  | `scaffold.command.import`  |
| `/worlds`  | Show all worlds.  | `scaffold.command.worlds`  |

<sup name="footnote1">[1]</sup> When importing maps, you must put a `level.dat` file and the `regions` folder in the root directory of a .zip file for Scaffold to recognize it as a valid Minecraft world.

## Support
If you need any additional support with server setup or using Scaffold, you may visit the [#contributing](https://warz.one/discord) channel in the Warzone Discord server.

## Acknowledgements & License
This project was originally based on a fork of Avicus Network's [Scaffold Build Plugin](https://github.com/Avicus/Scaffold) that was later updated for Minecraft 1.16.5 by Minehut and Warzone contributors. Kudos to them for their great work. 

Additionally, Scaffold is made available under the [MIT License](https://github.com/Warzone/Scaffold/blob/master/LICENSE), which allows you to modify any aspects of this project as you see fit as long the original license is preserved.

Credit for the net.querz package goes to Christopher White (https://github.com/cswhite2000) and Querz.
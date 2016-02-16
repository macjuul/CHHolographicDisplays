# CHHolographicDisplays
CHHD Adds support for the Holographic Displays plugin into CommandHelper. This allows you to create, modify and remove holograms in your worlds. However, these holograms are not the same ones you create using the Holographic Displays command. If you find a bug or want to request a new feature simply navigate to the issues page and report a new bug or enchancement.

Latest release: [download v1.0.0](https://github.com/macjuul/CHHolographicDisplays/releases/tag/v1.0.0)

## Functions:
+ **chhd_create_hologram([Hologram id], location array, line array | [String ID], location array, line)** - Create a new hologram with the given text. If no id is given a random one is generated and returned
+ **chhd_create_hologram(Hologram id)** - Remove an existing hologram
+ **chhd_list_holograms()** - Returns a list of holograms
+ **chhd_hologram_addline(Hologram id, String line|Array item, [Closure actionCode])** - Adds a new text or item line to a hologram. The string can accept a normal text string or an item array. You may also supply a closure that gets executed when a player interacts with the hologram that contains this line. If this is a text line, this close will get executed when someone clicks it. When this is an item line, this close gets executed when a player would walk through the item. This last feature is very handy for things like powerups and health kits :)
+ **chhd_hologram_removeline(Hologram id, Line number)** - Remove a line from a Hologram
+ **chhd_hologram_setline(Hologram id, Int line, String text|Item array)** - Adds a new line to a hologram
+ **chhd_hologram_insertline(Hologram id, Int line, String text|Item array)** - Inserts a new line at the given line number
+ **chhd_get_hologram_loc(Hologram id)** - Get the location of the given hologram, returned as location array
+ **chhd_set_hologram_loc(Hologram id, Location array)** - Set the location of the given hologram
+ **chhd_hologram_timestamp(Hologram id)** - Get the unix timestamp of the time when the hologram was created
+ **chhd_hologram_visibility(Hologram id | Hologram id, null | Hologram id, player array)** - Sets the visibility of a hologram. If only the first argument is given it will return a list of players that can see the hologram. If the second argument is null the hologram will be visible to everyone again. If the second argument is an array of players it will make the hologram only visible to them

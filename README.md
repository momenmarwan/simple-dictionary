# simple-dictionary

A small Android dictionary app written in Java. You can add, edit, and delete
word/meaning entries, all kept in memory and shown in a RecyclerView.

## Features
- List of words with their meanings (RecyclerView + custom adapter)
- Add a new word with the floating action button
- Tap an item to edit it
- Long-press an item to delete it
- Data is kept across screen rotation using `onSaveInstanceState()`

## Screens
- **MainActivity** – shows the list and the add button
- **AddEditActivity** – form with Word and Meaning fields and a Save button

## Built with
- Java
- XML layouts
- RecyclerView
- Minimum SDK 21

# AsDiff

AsDiff is an Android Studio plugin for comparing two pasted text values with the IDE's native Diff Viewer.

## Features

- Two resizable, theme-aware editor panes with line numbers, paste, undo, and find support
- Native line and inline diff highlighting, synchronized scrolling, and change navigation
- Swap and clear controls
- Project-level input persistence between dialog openings
- `Tools > Compare Text...` menu action
- `Compare Selected Text (Auto-format JSON)` submenu in the editor context menu
- Use the selection as Left while keeping the previous Right text, or load Right from the text clipboard
- Automatic pretty-printing when either side contains a valid JSON object or array
- `Ctrl+Alt+Shift+D` shortcut on Windows/Linux and `Control+Option+Shift+D` on macOS

The comparison stays local. AsDiff does not send either input to a network service.

## Development

Requirements:

- Android Studio 2026.1.x (`AI-261`)
- JDK 21 or newer

The default target is `/Applications/Android Studio.app`. Override it when needed:

```bash
./gradlew build -PandroidStudioPath="/path/to/Android Studio.app"
```

Useful tasks:

```bash
./gradlew test
./gradlew buildPlugin
./gradlew runIde
```

The installable ZIP is generated under `build/distributions/`.

## Use

1. Open an Android Studio project.
2. Choose `Tools > Compare Text...`, or select editor text and open `Compare Selected Text (Auto-format JSON)` from the context menu.
3. Choose whether to keep the previous Right text or load Right from the text clipboard.
4. Edit either side if needed, then select **Compare** to open the native Diff Viewer.

Inputs are stored in the current project's workspace settings so the dialog can be reopened without losing work.

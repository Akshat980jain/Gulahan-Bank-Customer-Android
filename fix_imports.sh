#!/bin/bash
find app/src/main/java/com/example/ui/screens -name "*.kt" -exec sed -i 's/import androidx.compose.material.icons.outlined.\*/import androidx.compose.material.icons.outlined.*\nimport androidx.compose.material.icons.automirrored.outlined.*/g' {} +
find app/src/main/java/com/example/ui/screens -name "*.kt" -exec sed -i 's/import androidx.compose.material.icons.filled.\*/import androidx.compose.material.icons.filled.*\nimport androidx.compose.material.icons.automirrored.filled.*/g' {} +

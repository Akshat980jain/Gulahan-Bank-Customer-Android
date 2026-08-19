#!/bin/bash
find app/src/main/java/com/example/ui/screens -name "*.kt" -exec sed -i 's/Icons.Filled.ArrowBack/Icons.AutoMirrored.Filled.ArrowBack/g' {} +
find app/src/main/java/com/example/ui/screens -name "*.kt" -exec sed -i 's/Icons.Filled.TrendingUp/Icons.AutoMirrored.Filled.TrendingUp/g' {} +
find app/src/main/java/com/example/ui/screens -name "*.kt" -exec sed -i 's/Icons.Outlined.TrendingUp/Icons.AutoMirrored.Outlined.TrendingUp/g' {} +
find app/src/main/java/com/example/ui/screens -name "*.kt" -exec sed -i 's/Icons.Outlined.Send/Icons.AutoMirrored.Outlined.Send/g' {} +
find app/src/main/java/com/example/ui/screens -name "*.kt" -exec sed -i 's/Icons.Filled.ArrowForward/Icons.AutoMirrored.Filled.ArrowForward/g' {} +
find app/src/main/java/com/example/ui/screens -name "*.kt" -exec sed -i 's/Icons.Outlined.HelpOutline/Icons.AutoMirrored.Outlined.HelpOutline/g' {} +
find app/src/main/java/com/example/ui/screens -name "*.kt" -exec sed -i 's/Icons.Outlined.Article/Icons.AutoMirrored.Outlined.Article/g' {} +
find app/src/main/java/com/example/ui/screens -name "*.kt" -exec sed -i 's/Icons.Outlined.Logout/Icons.AutoMirrored.Outlined.Logout/g' {} +

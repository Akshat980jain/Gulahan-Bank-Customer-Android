with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    lines = f.readlines()

new_lines = []
for line in lines:
    if line.strip() == "},":
        # Maybe it's this line? Let's check next line
        pass
    new_lines.append(line)

content = "".join(lines)
# Just do a blanket string replace without regex matching exactly
idx = content.find("MainBottomNavigation(currentScreen, onNavigate)")
if idx != -1:
    after = content[idx:]
    idx2 = after.find("{ paddingValues ->")
    if idx2 != -1:
        part_to_replace = after[:idx2]
        print(repr(part_to_replace))
        new_part = "MainBottomNavigation(currentScreen, onNavigate)\n        }\n    ) "
        content = content.replace(after[:idx2], new_part)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)

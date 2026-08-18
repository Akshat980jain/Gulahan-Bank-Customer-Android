with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "r") as f:
    content = f.read()

# Remove the floating action button
import re
content = re.sub(r'floatingActionButton = \{\s*ExtendedFloatingActionButton\([\s\S]*?\}\s*\)', '', content)

# Remove the AIAdvisorBanner item
content = content.replace("item { AIAdvisorBanner() }", "")

# We can also remove the AIAdvisorBanner function entirely
content = re.sub(r'@Composable\s*fun AIAdvisorBanner\(\)\s*\{[\s\S]*?\}\s*\}\s*\}', '', content)

with open("app/src/main/java/com/example/ui/screens/HomeScreen.kt", "w") as f:
    f.write(content)

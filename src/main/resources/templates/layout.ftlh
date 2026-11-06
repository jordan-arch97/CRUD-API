<#--
  layout.ftlh: the ONE shared page shell.
  Every page calls it like this:

    <#import "layout.ftlh" as layout>
    <@layout.page title="About" active="about">
      ...this page's own content goes here...
    </@layout.page>

  title  -> text for the <title> tag
  active -> which nav link to highlight: "home", "about", or "add"
            (leave it out on pages that are not in the nav, like details)
-->
<#macro page title active="">
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${title} - Overwatch Character Gallery</title>
  <link rel="stylesheet" href="/css/style.css">
</head>
<body>

  <!-- Navigation Bar (written once, shown on every page) -->
  <nav class="navbar">
    <div class="logo">OW <span>Hero Gallery</span></div>
    <ul class="nav-links">
      <li><a href="/"<#if active == "home"> class="active"</#if>>Home</a></li>
      <li><a href="/about"<#if active == "about"> class="active"</#if>>About</a></li>
      <li><a href="/characters/new"<#if active == "add"> class="active"</#if>>Add a Character</a></li>
    </ul>
  </nav>

  <#-- One-time success message. It only exists in the Model on the request
       right after a redirect that used RedirectAttributes.addFlashAttribute("flash", ...) -->
  <#if flash?has_content>
    <div class="flash" role="status">${flash}</div>
  </#if>

  <#-- This is where each page's own content is inserted -->
  <#nested>

</body>
</html>
</#macro>

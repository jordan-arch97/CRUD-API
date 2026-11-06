<#import "layout.ftlh" as layout>
<@layout.page title=hero.name>

  <div class="details-wrap">

    <div class="details-image">
      <img src="${hero.imageUrl?has_content?then(hero.imageUrl, '/images/placeholder.svg')}" alt="${hero.name}">
    </div>

    <div class="details-text">
      <#if hero.role?has_content>
        <span class="role-badge">${hero.role}</span>
      </#if>
      <h1>${hero.name}</h1>

      <h2>About this hero</h2>
      <p>${hero.description!''}</p>

      <#if hero.debutDate??>
        <h2>Debut</h2>
        <p>${hero.debutDate}</p>
      </#if>

      <div class="page-links">
        <a href="/" class="btn">Back to Gallery</a>
        <#-- Edit and Delete buttons get added in Step 7 -->
      </div>
    </div>

  </div>

</@layout.page>

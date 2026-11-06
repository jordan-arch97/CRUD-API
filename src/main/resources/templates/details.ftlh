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
        <a href="/characters/${hero.characterId?c}/edit" class="btn btn-outline">Edit</a>
        <#-- Delete is a POST form (a link would send GET). The browser asks first; Cancel stops the submit. -->
        <form class="inline-form" action="/characters/${hero.characterId?c}/delete" method="post"
              onsubmit="return confirm('Delete this hero? This cannot be undone.');">
          <button type="submit" class="btn btn-danger">Delete</button>
        </form>
      </div>
    </div>

  </div>

</@layout.page>

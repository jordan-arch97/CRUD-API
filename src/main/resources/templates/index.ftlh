<#import "layout.ftlh" as layout>
<@layout.page title="Home" active="home">

  <!-- Hero Banner -->
  <section class="hero-banner">
    <h1>Overwatch Character Gallery</h1>
    <p>A fan-made showcase of heroes from the Overwatch universe.</p>
  </section>

  <#-- Search bar goes here in Step 5 -->

  <!-- Character Gallery -->
  <section class="section-wrap">
    <h2>Meet the Heroes</h2>

    <#if heroes?has_content>
      <p class="result-count">Showing ${heroes?size} <#if heroes?size == 1>hero<#else>heroes</#if></p>

      <div class="card-grid">
        <#list heroes as c>
          <div class="character-card">
            <#-- imageUrl can be null OR "" for old rows, so test with ?has_content (a "!" default only covers null) -->
            <img src="${c.imageUrl?has_content?then(c.imageUrl, '/images/placeholder.svg')}" alt="${c.name}">
            <div class="card-body">
              <h3>${c.name}</h3>
              <#if c.role?has_content>
                <span class="role-badge">${c.role}</span>
              </#if>
              <p class="summary">${(c.description!'')?truncate(140)}</p>
              <#-- ?c keeps the ID as plain digits (1234, not 1,234) -->
              <a href="/characters/${c.characterId?c}" class="btn">View Details</a>
            </div>
          </div>
        </#list>
      </div>
    <#else>
      <div class="empty-state">
        <p>No characters found.</p>
      </div>
    </#if>
  </section>

</@layout.page>

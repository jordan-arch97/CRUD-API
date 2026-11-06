<#import "layout.ftlh" as layout>
<@layout.page title="Home" active="home">

  <!-- Hero Banner -->
  <section class="hero-banner">
    <h1>Overwatch Character Gallery</h1>
    <p>A fan-made showcase of heroes from the Overwatch universe.</p>
  </section>

  <!-- Search bar: a GET form, so the search ends up in the URL (/?name=...&role=...) -->
  <form class="search-section" action="/" method="get">
    <input type="text" name="name" placeholder="Search heroes by name..." value="${searchName!''}">
    <select name="role">
      <option value="">All roles</option>
      <#list ["Damage", "Tank", "Support"] as r>
        <option value="${r}"<#if (searchRole!'') == r> selected</#if>>${r}</option>
      </#list>
    </select>
    <button class="btn" type="submit">Search</button>
    <a class="btn btn-outline" href="/">Clear</a>
  </form>

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
        <p>No characters found. Try a different name or role.</p>
      </div>
    </#if>
  </section>

</@layout.page>

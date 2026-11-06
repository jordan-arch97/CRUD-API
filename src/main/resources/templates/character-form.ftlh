<#import "layout.ftlh" as layout>
<#--
  One form used for BOTH add and edit. The controller sets:
    hero         -> the object to show (empty for add, loaded for edit, or what was typed after an error)
    formTitle    -> page heading
    formAction   -> where the form posts to
    submitLabel  -> button text
    navActive    -> "add" on the add page, "" on the edit page
    errors       -> (only after a failed save) map of field name -> message
  Every input's name="" must match a property name on OverwatchHero, or the value arrives empty.
-->
<@layout.page title=formTitle active=navActive>

  <div class="form-wrap">
    <h1>${formTitle}</h1>
    <p>Fill out the form below. Name and description are required.</p>

    <form class="character-form" action="${formAction}" method="post">

      <#if errors?has_content>
        <div class="form-error-banner" role="alert">Please fix the problems below and try again.</div>
      </#if>

      <div class="form-group">
        <label for="name">Hero Name</label>
        <input type="text" id="name" name="name" placeholder="e.g. Winston" value="${hero.name!''}">
        <#if (errors.name)??><p class="field-error">${errors.name}</p></#if>
      </div>

      <div class="form-group">
        <label for="role">Role</label>
        <select id="role" name="role">
          <option value="">Select a role</option>
          <#list ["Damage", "Tank", "Support"] as r>
            <option value="${r}"<#if (hero.role!'') == r> selected</#if>>${r}</option>
          </#list>
        </select>
        <#if (errors.role)??><p class="field-error">${errors.role}</p></#if>
      </div>

      <div class="form-group">
        <label for="debutDate">Debut Date</label>
        <input type="date" id="debutDate" name="debutDate" value="${(hero.debutDate)!''}">
        <#if (errors.debutDate)??><p class="field-error">${errors.debutDate}</p></#if>
      </div>

      <div class="form-group">
        <label for="imageUrl">Image URL</label>
        <input type="url" id="imageUrl" name="imageUrl" placeholder="https://example.com/image.png" value="${hero.imageUrl!''}">
        <#if (errors.imageUrl)??><p class="field-error">${errors.imageUrl}</p></#if>
      </div>

      <div class="form-group">
        <label for="description">Short Description</label>
        <textarea id="description" name="description" placeholder="A quick summary of who this hero is...">${hero.description!''}</textarea>
        <#if (errors.description)??><p class="field-error">${errors.description}</p></#if>
      </div>

      <div class="form-actions">
        <button type="submit" class="btn">${submitLabel}</button>
        <a href="/" class="btn btn-outline">Cancel</a>
      </div>

    </form>
  </div>

</@layout.page>

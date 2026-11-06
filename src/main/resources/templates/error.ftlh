<#import "layout.ftlh" as layout>
<@layout.page title="Not found">

  <div class="about-wrap">
    <h1>Hero not found</h1>
    <#-- errorMessage is set by the page controller. When Spring Boot renders this
         template for some other bad URL, it is missing, so give it a default. -->
    <p>${errorMessage!"We couldn't find the page you were looking for."}</p>
    <div class="page-links">
      <a href="/" class="btn">Back to Gallery</a>
    </div>
  </div>

</@layout.page>

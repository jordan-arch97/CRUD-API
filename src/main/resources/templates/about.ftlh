<#import "layout.ftlh" as layout>
<@layout.page title="About" active="about">

  <div class="about-wrap">

    <div class="about-flex">
      <img src="https://i.ytimg.com/vi/GKXS_YA9s7E/sddefault.jpg" alt="Overwatch heroes">
      <div class="about-text">
        <h1>Welcome to the Overwatch Character Gallery</h1>
        <p>The Overwatch Character Gallery showcases iconic heroes from Blizzard's Overwatch franchise. Browse the gallery, search by name or role, and open any hero to read about them.</p>
        <p>The gallery is no longer a fixed list. You can add new heroes, edit the ones that are already here, and remove the ones you don't want, whether they are Damage, Tank or Support.</p>
      </div>
    </div>

    <h2>How this app is built</h2>
    <p>This is a Spring Boot application with a layered design. Each layer has one job and only talks to the layer next to it.</p>
    <ul class="ability-list">
      <li><strong>Entity:</strong> the OverwatchHero class describes a hero (name, description, role, debut date and image link) and is stored as a row in the characters table.</li>
      <li><strong>Repository:</strong> a Spring Data JPA interface that reads and writes heroes in a PostgreSQL database hosted on Neon.</li>
      <li><strong>Service:</strong> holds the logic, such as searching by name and role together, updating an existing hero, and reporting a missing hero as not found.</li>
      <li><strong>Controllers:</strong> a REST controller serves JSON at <code>/api/characters</code>, and a separate page controller serves the web pages you are looking at. Both call the same service.</li>
      <li><strong>Templates:</strong> FreeMarker templates turn each hero into HTML on the server, and every page shares one layout so the navigation bar is written only once.</li>
    </ul>

    <h2>How the front end and back end work together</h2>
    <p>When you open a page, your browser sends a request to the page controller. The controller asks the service for the data, puts it in the model, and names a template. FreeMarker fills that template in and sends finished HTML back, so the front end is built on the server rather than in your browser.</p>
    <p>The search bar is a form that sends the name and role to the gallery page in the web address. The add and edit forms send their values to the server, which checks them and either shows the form again with messages or saves the hero. After a successful save, the server redirects you to another page, so refreshing the browser never submits the form a second time. The short green message at the top of the page is shown once, right after that redirect.</p>
    <p>The REST API still runs next to the pages, so other programs can read and change the same heroes through JSON without using the website.</p>

    <h2>Data &amp; Attribution</h2>
    <p>The characters featured in this gallery are from the Overwatch universe. All character descriptions and abilities are based on their appearances in the officially licensed Overwatch games. Images are sourced from publicly available character artwork for demonstration purposes.</p>
  </div>

</@layout.page>

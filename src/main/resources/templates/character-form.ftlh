<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Add a Character - Overwatch Character Gallery</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>

  <!-- Navigation Bar -->
  <nav class="navbar">
    <div class="logo">OW <span>Hero Gallery</span></div>
    <ul class="nav-links">
      <li><a href="index.html">Home</a></li>
      <li><a href="details.html">Details</a></li>
      <li><a href="about.html">About</a></li>
      <li><a href="new-character-form.html" class="active">Add a Character</a></li>
    </ul>
  </nav>

  <div class="form-wrap">
    <h1>Add a New Hero</h1>
    <p>Fill out the form below to add a new character to the gallery. (This form is just for show right now -- it doesn't save anything yet.)</p>

    <form class="character-form">

      <div class="form-group">
        <label for="hero-name">Hero Name</label>
        <input type="text" id="hero-name" name="hero-name" placeholder="e.g. Winston">
      </div>

      <div class="form-group">
        <label for="universe">Universe</label>
        <input type="text" id="universe" name="universe" placeholder="e.g. Overwatch">
      </div>

      <div class="form-group">
        <label for="role">Role</label>
        <select id="role" name="role">
          <option value="">Select a role</option>
          <option value="damage">Damage</option>
          <option value="tank">Tank</option>
          <option value="support">Support</option>
        </select>
      </div>

      <div class="form-group">
        <label for="image-url">Image URL</label>
        <input type="url" id="image-url" name="image-url" placeholder="https://example.com/image.png">
      </div>

      <div class="form-group">
        <label for="description">Short Description</label>
        <textarea id="description" name="description" placeholder="A quick summary of who this hero is..."></textarea>
      </div>

      <button type="submit" class="btn">Add Hero to Gallery</button>

    </form>
  </div>
</body>
</html>

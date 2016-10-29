# Require any additional compass plugins here.

# set the css file encoding
Encoding.default_external = "utf-8"

# Set this to the root of your project when deployed:
http_path = "/"         #项目根目录
css_dir = "src/css"     #css文件放在css目录
sass_dir = "src/sass"   #sass文件放在sass目录

images_dir = "src/img"  #图片文件放在img目录
generated_images_dir = "src/img"

# You can select your preferred output style here (can be overridden via the command line):
# output_style = :expanded or :nested or :compact or :compressed

output_style = :compressed


# To enable relative paths to assets via compass helper functions. Uncomment:
relative_assets = true

# To disable debugging comments that display the original location of your selectors. Uncomment:
line_comments = false

#缓存文件生成设置
cache = false


# @see http://stackoverflow.com/questions/14173242/remove-the-random-string-appended-to-sprite-filename-with-compass-sass
module Compass::SassExtensions::Functions::Sprites
  def sprite_url(map)
    verify_map(map, "sprite-url")
    map.generate
    generated_image_url(Sass::Script::String.new(map.name_and_hash))
  end
end

module Compass::SassExtensions::Sprites::SpriteMethods
  def name_and_hash
    "sprites-#{path}.png"
  end

  def cleanup_old_sprites
    Dir[File.join(::Compass.configuration.generated_images_path, "sprites-#{path}.png")].each do |file|
      log :remove, file
      FileUtils.rm file
      ::Compass.configuration.run_sprite_removed(file)
    end
  end
end

module Compass
  class << SpriteImporter
    def find_all_sprite_map_files(path)
      glob = "sprites-*{#{self::VALID_EXTENSIONS.join(",")}}"
      Dir.glob(File.join(path, "**", glob))
    end
  end
end

in vec2 pass_texCoord;
uniform sampler2D texture_diffuse;
varying out vec4 out_Color;

void main(void) {



    out_Color = texture(texture_diffuse, pass_texCoord);

//    out_Color = vec4(pass_TextureCoord.y,0,0,1);
}
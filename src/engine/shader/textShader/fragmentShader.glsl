in vec2 pass_texCoord;
uniform vec4 text_Color;
uniform sampler2D textureSampler;
varying out vec4 out_Color;

void main(void) {
    vec4 charTexturColor = texture(textureSampler, pass_texCoord);
    if(charTexturColor.x<0.1){
        discard;
    } else {
        out_Color = text_Color;
    }
}
in vec4 in_Position;
in vec2 in_texCoord;

uniform mat4 transformationMatrix;
uniform vec4 charOffset;

varying out vec2 pass_texCoord;

void main(void) {
  	pass_texCoord = vec2(charOffset.x + (in_texCoord.x * charOffset.z), -1*(charOffset.y + (in_texCoord.y * charOffset.w)));
  	//pass_texCoord = in_texCoord;
  	gl_Position = transformationMatrix * in_Position;
}
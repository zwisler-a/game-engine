in vec4 in_Position;
in vec2 in_texCoord;

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform mat4 scale;

varying out vec2 pass_texCoord;

void main(void) {
  	// gl_Position = vec4(in_Position.x * scale, in_Position.y * scale, in_Position.z * scale,in_Position.w * scale);
  	pass_texCoord = in_texCoord ;
  	gl_Position = projectionMatrix * in_Position;
}
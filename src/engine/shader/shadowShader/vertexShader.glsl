in vec4 in_Position;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;

vec4 worldPos;

varying out vec2 pass_texCoord;

void main(void) {

  	worldPos = transformationMatrix * in_Position;
	gl_Position = projectionMatrix * viewMatrix * worldPos;
}
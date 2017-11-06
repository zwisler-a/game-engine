in vec4 in_Position;
in vec2 in_TextureCoord;
in vec3 in_normal;

varying out vec3 vecToCamera;
varying out vec4 clipSpace;
varying out vec2 out_TextureCoord;


vec4 worldPos;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPos[4];
uniform vec3 cameraPosition;

void main(void) {
  	worldPos = transformationMatrix * in_Position;
	clipSpace = projectionMatrix * viewMatrix * worldPos;
	gl_Position = clipSpace;
	out_TextureCoord = in_TextureCoord * 20;
	vecToCamera = cameraPosition - worldPos.xyz;
}
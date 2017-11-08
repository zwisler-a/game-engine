in vec4 in_Position;
in vec2 in_TextureCoord;
in vec3 in_normal;

varying out vec2 pass_TextureCoord;
varying out vec3 out_normal;
varying out vec3 out_toLight[4];
varying out float distance[4];


vec4 worldPos;

uniform vec4 clipPlane;
uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPos[4];
uniform sampler2DShadow depthSampler;

void main(void) {


  	worldPos = transformationMatrix * in_Position;
    gl_ClipDistance[0] = dot(worldPos,clipPlane);
	gl_Position = projectionMatrix * viewMatrix * worldPos;

	pass_TextureCoord = in_TextureCoord;
	
	out_normal = (transformationMatrix * vec4(in_normal,0.0)).xyz;
	for(int i=0;i<4;++i) {
		out_toLight[i] = (lightPos[i] - worldPos.xyz);
		distance[i] = length(lightPos[i] - worldPos.xyz);
	}
}
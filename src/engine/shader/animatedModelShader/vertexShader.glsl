
const int MAX_JOINTS = 50;
const int MAX_WEIGHTS = 3;

in vec4 in_Position;
in vec2 in_TextureCoord;
in vec3 in_normal;
in ivec3 in_jointIds;
in vec3 in_weights;

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
uniform mat4 jointTransforms[MAX_JOINTS];
uniform sampler2DShadow depthSampler;

void main(void) {


    vec4 totalLocalPos = vec4(0.0);
	vec4 totalNormal = vec4(0.0);

	for(int i=0;i<MAX_WEIGHTS;i++){
		mat4 jointTransform = jointTransforms[in_jointIds[i]];

		vec4 posePosition = jointTransform * in_Position;
		totalLocalPos += posePosition * in_weights[i];

		vec4 worldNormal = jointTransform * vec4(in_normal, 0.0);
		totalNormal += worldNormal * in_weights[i];
	}

  	worldPos = transformationMatrix * totalLocalPos;
    gl_ClipDistance[0] = dot(worldPos,clipPlane);
	gl_Position = projectionMatrix * viewMatrix * worldPos;

	pass_TextureCoord = in_TextureCoord;
	
	out_normal = (transformationMatrix * vec4(in_normal,0)).xyz;
	for(int i=0;i<4;++i) {
		out_toLight[i] = (lightPos[i] - worldPos.xyz);
		distance[i] = length(lightPos[i] - worldPos.xyz);
	}
}
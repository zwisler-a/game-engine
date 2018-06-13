uniform sampler2D texture_diffuse;
uniform vec3 lightColor[4];
uniform float intensity[4];

in vec2 pass_TextureCoord;
in vec3 out_normal;
in vec3 out_toLight[4];
in float distance[4];


varying out vec4 out_Color;

void main(void) {

    out_Color = vec4(out_normal,0);
	vec3 unitNormal = normalize(out_normal);
	vec3 totalDiffuse = vec3(0);
	
	for(int i=0;i<4;++i) {
		vec3 unitToLight = normalize(out_toLight[i]);
		float nDot = dot(unitNormal,unitToLight);
		float brightness = max(nDot,0.0) * (1/(distance[i]))* intensity[i];
		vec3 diffuse = brightness * lightColor[i] ;
		totalDiffuse = totalDiffuse + diffuse;
	} 
	totalDiffuse = max(totalDiffuse,0.2);
	vec4 textureColor = texture(texture_diffuse, pass_TextureCoord);
	if(textureColor.w <0.1){
		discard;
	} else {
		out_Color = vec4(totalDiffuse,1.0)/2 * texture(texture_diffuse, pass_TextureCoord);
	}


}
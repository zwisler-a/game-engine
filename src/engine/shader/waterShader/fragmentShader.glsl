uniform sampler2D refractionTexture;
uniform sampler2D reflectionTexture;
uniform sampler2D refractionDepthTexture;
uniform sampler2D dudvTexture;

uniform vec3 lightColor[4];
uniform float intensity[4];

uniform float distortionMultiplier;
uniform float timeFactor;

in vec3 vecToCamera;
in vec4 clipSpace;
in vec2 out_TextureCoord;

vec4 reflectionTextureColor;
vec4 refractionTextureColor;

vec2 distortion;
vec2 distortion2;

varying out vec4 out_Color;

void main(void) {

    vec3 viewVector = normalize(vecToCamera);
    float fresnelDot = dot(viewVector, vec3(0.0,1.0,0.0));
    float fresnel = pow(fresnelDot,0.1);

    vec2 ndc = ((vec2(clipSpace.x,clipSpace.y*-1.0)/clipSpace.w)/2.0 + 0.5);

    distortion = (texture(dudvTexture, vec2(out_TextureCoord.x + timeFactor,out_TextureCoord.y)).xy * 2.0 - 1.0) * distortionMultiplier;
    distortion2 = (texture(dudvTexture, vec2(out_TextureCoord.x ,out_TextureCoord.y + timeFactor)).xy * 2.0 - 1.0) * distortionMultiplier;

    distortion = mix(distortion,distortion2,0.5);

    vec2 reflectionCoords = vec2(ndc.x,ndc.y);
    vec2 refractionCoords = vec2(ndc.x,-ndc.y);

    reflectionCoords += distortion;
    reflectionCoords = clamp(reflectionCoords,0.001,0.999);
    refractionCoords += distortion;
    refractionCoords.x = clamp(refractionCoords.x, 0.001, 0.999);
    refractionCoords.y = clamp(refractionCoords.y, -0.999, -0.001);

    reflectionTextureColor = texture(reflectionTexture, reflectionCoords);
    refractionTextureColor = texture(refractionTexture, refractionCoords);
    vec4 color = mix(reflectionTextureColor, refractionTextureColor,  fresnel);
    float depth = pow(1,100);

    color.x -= 0.1;
    color.y -= 0.1;

    out_Color = color;
    //out_Color *= texture(refractionDepthTexture,refractionCoords);
}
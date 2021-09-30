#version 330 core

in vec2 pass_textureCoords;

out vec4 out_Colour;

uniform sampler2D textureSampler;
uniform int highlight;
uniform int possibleMove;

void main() {
    if(highlight == 1){
        out_Colour = vec4(0,0.3,0,0.3) + texture(textureSampler, pass_textureCoords);
    } else if(possibleMove == 1){
        out_Colour = vec4(0,0,0.3,0.3) + texture(textureSampler, pass_textureCoords);
    } else{
        out_Colour = texture(textureSampler, pass_textureCoords);
    }


}
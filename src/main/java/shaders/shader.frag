#version 330 core

in vec2 pass_textureCoords;

out vec4 out_Colour;

uniform sampler2D textureSampler;

void main() {
    out_Colour = texture(textureSampler, pass_textureCoords);
}
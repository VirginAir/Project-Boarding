#version 330

layout (location = 0) in vec3 VertexPosition;
layout (location = 1) in vec3 VertexColour;

uniform mat4 modelViewMatrix;

out vec3 Colour;

void main(){
    Colour = VertexColour;
    gl_Position = modelViewMatrix * vec4(VertexPosition, 1.0);
}
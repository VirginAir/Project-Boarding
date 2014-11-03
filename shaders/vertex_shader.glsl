#version 430

layout (location = 0) in vec3 VertexPosition;
layout (location = 1) in vec3 VertexColour;

layout (location = 2) uniform mat4 modelViewMatrix;

out vec3 Colour;

void main(){
    Colour = VertexColour;
    gl_Position = modelViewMatrix * vec4(VertexPosition, 1.0);
}
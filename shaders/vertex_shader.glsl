#version 330

layout (location = 0) in vec3 VertexPosition;
layout (location = 1) in vec2 VertexUV;

uniform mat4 modelViewMatrix;

out vec2 FragUV;

void main(){
    FragUV = VertexUV;
    gl_Position = modelViewMatrix * vec4(VertexPosition, 1.0);
}
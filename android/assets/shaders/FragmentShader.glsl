#version 100

#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec2 v_lightPos;
varying vec3 v_lightColor;
varying vec2 v_position;
varying vec4 v_spaceColor;

uniform sampler2D u_texture;

void main() {

    for(int row = 0; row < 2; row++) {
        for(int col = 0; col < 2; col++) {
            float dist = distance(v_position, vec2(-1 + col, 1 - row));
            float delta = 0.1;
            float alpha = smoothstep(100.0-delta, 100.0, dist);

            if(dist > 23.0){
            gl_FragColor = mix(v_spaceColor, v_color, alpha);
            }
            else{
            gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
            }
        }
    }

}

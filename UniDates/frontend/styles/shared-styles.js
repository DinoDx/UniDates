// eagerly import theme styles so as we can override them
import '@vaadin/vaadin-lumo-styles/all-imports';

const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `
<custom-style>
  <style>
    html {
      --lumo-primary-color: hsla(68, 57%, 22%, 0.34);
      --lumo-primary-text-color: hsl(300,3%,7%);
    }
  </style>
</custom-style>

<dom-module id="button-style" theme-for="vaadin-button">
  <template>
    <style>
      :host(:not([theme~="tertiary"])) {
        /*background-image: linear-gradient(var(--lumo-tint-5pct), var(--lumo-shade-5pct));*/
        box-shadow: inset 0 0 0 1px var(--lumo-contrast-20pct);
      }

      :host(:not([theme~="tertiary"]):not([theme~="primary"]):not([theme~="error"]):not([theme~="success"])) {
        color: var(--lumo-body-text-color);
      }

      :host([theme~="primary"]) {
        text-shadow: 0 -1px 0 var(--lumo-shade-20pct);
      }
    </style>
  </template>
</dom-module>

<dom-module id="text-field-style" theme-for="vaadin-text-field vaadin-text-area vaadin-password-field">
  <template>
    <style>
      [part="input-field"] {
        box-shadow: inset 0 0 0 1px var(--lumo-contrast-30pct);
        background-color: var(--lumo-base-color);
      }

      :host([invalid]) [part="input-field"] {
        box-shadow: inset 0 0 0 1px var(--lumo-error-color);
      }
    </style>
  </template>
</dom-module>

`;

document.head.appendChild($_documentContainer.content);

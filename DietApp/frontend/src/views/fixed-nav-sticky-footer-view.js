import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-button/vaadin-button.js';
import '../components/ex-card.js';

class FixedNavStickyFooterView extends PolymerElement {

    static get template() {
        return html`
<style include="common-styles">
                :host {
                    display: flex;
                    flex-direction: column;
                    height: 100%;
                }

                .wrapper {
                    margin: 0 auto; /* Horizontally center the layout grid if there is space around it */
                    max-width: 1440px;
                    padding: 0 var(--lumo-space-m);
                }

                #header {
                    z-index: 1;
                    position: fixed;
                    right: 0;
                    left: 0;
                    display: flex;
                    white-space: nowrap;
                    flex-wrap: wrap;
                    align-items: center;
                    padding: 0 var(--lumo-space-s);
                    --lumo-primary-text-color: var(--lumo-contrast-90pct);
                }

                .searchField {
                    padding: var(--lumo-space-s) 0;
                    margin-right: var(--lumo-space-s);
                }

                #header a {
                    display: inline-block;
                    margin: var(--lumo-space-s);
                }

                #header .titleLink {
                    font-size: var(--lumo-font-size-xl);
                    margin-right: var(--lumo-space-m);
                }

                main {
                    flex-shrink: 0;
                }


                #main {
                    margin-top: 100px;
                }

                footer {
                    margin-top: auto;
                    background-color: var(--lumo-shade-10pct);
                    padding: var(--lumo-space-wide-m);
                }
            </style>
<header id="header" theme="dark"></header>
<main>
 <div id="main" class="wrapper"></div>
</main>
<footer>
 <div id="footer" class="wrapper"></div>
</footer>
`;
    }

    static get is() {
        return 'fixed-nav-sticky-footer-view';
    }
}

customElements.define(FixedNavStickyFooterView.is, FixedNavStickyFooterView);

import {Component} from '@angular/core';

@Component({
  selector: 'agent-spinner',
  template: `
    <div class="flex justify-content-center">
      <video
        autoplay
        loop
        muted
        playsinline
        preload="auto"
        width="180"
        height="180"
        style="pointer-events: none;">
        <source src="assets/images/spinner.webm" type="video/webm">
      </video>
    </div>
  `,
  standalone: true
})
export class AgentSpinnerComponent {

}

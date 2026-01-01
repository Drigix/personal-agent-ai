import {ComponentFactoryResolver, Directive, Input, TemplateRef, ViewContainerRef} from '@angular/core';
import {AgentSpinnerComponent} from './spinner.component';

@Directive({selector: '[isLoading]'})
export class AgentSpinnerDirective {
  @Input()
  set isLoading(isLoading: boolean) {
    if (isLoading) {
      const componentFactory = this.componentFactoryResolver.resolveComponentFactory(AgentSpinnerComponent);
      this.view.createComponent(componentFactory);
    } else {
      this.view.clear();
      this.view.createEmbeddedView(this.template)
    }
  };
  constructor(private view: ViewContainerRef, private componentFactoryResolver: ComponentFactoryResolver, private template: TemplateRef<AnimationPlayState>) {

  }
}

import {Button} from 'primeng/button';
import {AutoComplete} from 'primeng/autocomplete';
import {FloatLabel} from 'primeng/floatlabel';
import {InputNumber} from 'primeng/inputnumber';
import {InputText} from 'primeng/inputtext';
import {RadioButton} from 'primeng/radiobutton';
import {Checkbox} from 'primeng/checkbox';
import {Textarea} from 'primeng/textarea';
import {DatePicker} from 'primeng/datepicker';
import {CascadeSelect} from 'primeng/cascadeselect';
import {MultiSelect} from 'primeng/multiselect';
import {SplitButton} from 'primeng/splitbutton';
import {Accordion} from 'primeng/accordion';
import {Card} from 'primeng/card';
import {Divider} from 'primeng/divider';
import {Tab, TabList, TabPanel, TabPanels, Tabs} from 'primeng/tabs';
import {ConfirmDialog} from 'primeng/confirmdialog';
import {Dialog} from 'primeng/dialog';
import {Popover} from 'primeng/popover';
import {ConfirmPopup} from 'primeng/confirmpopup';
import {Tooltip} from 'primeng/tooltip';
import {Message} from 'primeng/message';
import {Toast} from 'primeng/toast';
import {Avatar} from 'primeng/avatar';
import {Tag} from 'primeng/tag';
import {Skeleton} from 'primeng/skeleton';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateDirective, TranslatePipe} from '@ngx-translate/core';
import {AgentSpinnerDirective} from './directives/spinner/spinner.directive';
import {AgentSpinnerComponent} from './directives/spinner/spinner.component';

export const COMMON_IMPORTS =
  [CommonModule, RouterModule, TranslateDirective, TranslatePipe, Skeleton];

export const FORMS_IMPORTS =
  [FormsModule, ReactiveFormsModule];

export const PRIMENG_BUTTONS_COMPONENTS =
  [Button, RadioButton, Checkbox, SplitButton];

export const PRIMENG_LABEL_COMPONENTS =
  [AutoComplete, FloatLabel, InputNumber, InputText, Textarea, DatePicker, CascadeSelect, MultiSelect];

export const PRIMENG_PANEL_COMPONENTS =
  [Accordion, Card, Divider, Tabs, TabPanels, TabPanel, Tab, TabList];

export const PRIMENG_OVERLAY_COMPONENTS =
  [ConfirmDialog, Dialog, Popover, ConfirmPopup, Tooltip];

export const PRIMENG_MESSAGE_COMPONENTS =
  [Message, Toast];

export const PRIMENG_MISC_COMPONENTS =
  [Avatar, Tag, Skeleton];

export const CUSTOM_DIRECTIVES = [
  AgentSpinnerDirective, AgentSpinnerComponent
]

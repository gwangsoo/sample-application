/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import LanguageUpdate from './language-update.vue';
import LanguageService from './language.service';
import AlertService from '@/shared/alert/alert.service';

type LanguageUpdateComponentType = InstanceType<typeof LanguageUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const languageSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<LanguageUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Language Management Update Component', () => {
    let comp: LanguageUpdateComponentType;
    let languageServiceStub: SinonStubbedInstance<LanguageService>;

    beforeEach(() => {
      route = {};
      languageServiceStub = sinon.createStubInstance<LanguageService>(LanguageService);
      languageServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          languageService: () => languageServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(LanguageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.language = languageSample;
        languageServiceStub.update.resolves(languageSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(languageServiceStub.update.calledWith(languageSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        languageServiceStub.create.resolves(entity);
        const wrapper = shallowMount(LanguageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.language = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(languageServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        languageServiceStub.find.resolves(languageSample);
        languageServiceStub.retrieve.resolves([languageSample]);

        // WHEN
        route = {
          params: {
            languageId: '' + languageSample.id,
          },
        };
        const wrapper = shallowMount(LanguageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.language).toMatchObject(languageSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        languageServiceStub.find.resolves(languageSample);
        const wrapper = shallowMount(LanguageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});

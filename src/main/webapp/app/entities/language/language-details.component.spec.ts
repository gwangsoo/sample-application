/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import LanguageDetails from './language-details.vue';
import LanguageService from './language.service';
import AlertService from '@/shared/alert/alert.service';

type LanguageDetailsComponentType = InstanceType<typeof LanguageDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const languageSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Language Management Detail Component', () => {
    let languageServiceStub: SinonStubbedInstance<LanguageService>;
    let mountOptions: MountingOptions<LanguageDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      languageServiceStub = sinon.createStubInstance<LanguageService>(LanguageService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          languageService: () => languageServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        languageServiceStub.find.resolves(languageSample);
        route = {
          params: {
            languageId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(LanguageDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.language).toMatchObject(languageSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        languageServiceStub.find.resolves(languageSample);
        const wrapper = shallowMount(LanguageDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});

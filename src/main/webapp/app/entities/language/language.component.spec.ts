/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Language from './language.vue';
import LanguageService from './language.service';
import AlertService from '@/shared/alert/alert.service';

type LanguageComponentType = InstanceType<typeof Language>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Language Management Component', () => {
    let languageServiceStub: SinonStubbedInstance<LanguageService>;
    let mountOptions: MountingOptions<LanguageComponentType>['global'];

    beforeEach(() => {
      languageServiceStub = sinon.createStubInstance<LanguageService>(LanguageService);
      languageServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          languageService: () => languageServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        languageServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(Language, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(languageServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.languages[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: LanguageComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Language, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        languageServiceStub.retrieve.reset();
        languageServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        languageServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeLanguage();
        await comp.$nextTick(); // clear components

        // THEN
        expect(languageServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(languageServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});

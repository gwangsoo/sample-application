/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import AffiliatedInfo from './affiliated-info.vue';
import AffiliatedInfoService from './affiliated-info.service';
import AlertService from '@/shared/alert/alert.service';

type AffiliatedInfoComponentType = InstanceType<typeof AffiliatedInfo>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('AffiliatedInfo Management Component', () => {
    let affiliatedInfoServiceStub: SinonStubbedInstance<AffiliatedInfoService>;
    let mountOptions: MountingOptions<AffiliatedInfoComponentType>['global'];

    beforeEach(() => {
      affiliatedInfoServiceStub = sinon.createStubInstance<AffiliatedInfoService>(AffiliatedInfoService);
      affiliatedInfoServiceStub.retrieve.resolves({ headers: {} });

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
          affiliatedInfoService: () => affiliatedInfoServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        affiliatedInfoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(AffiliatedInfo, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(affiliatedInfoServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.affiliatedInfos[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: AffiliatedInfoComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(AffiliatedInfo, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        affiliatedInfoServiceStub.retrieve.reset();
        affiliatedInfoServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        affiliatedInfoServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeAffiliatedInfo();
        await comp.$nextTick(); // clear components

        // THEN
        expect(affiliatedInfoServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(affiliatedInfoServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});

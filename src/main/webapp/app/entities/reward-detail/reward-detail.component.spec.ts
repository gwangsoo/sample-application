/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import RewardDetail from './reward-detail.vue';
import RewardDetailService from './reward-detail.service';
import AlertService from '@/shared/alert/alert.service';

type RewardDetailComponentType = InstanceType<typeof RewardDetail>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('RewardDetail Management Component', () => {
    let rewardDetailServiceStub: SinonStubbedInstance<RewardDetailService>;
    let mountOptions: MountingOptions<RewardDetailComponentType>['global'];

    beforeEach(() => {
      rewardDetailServiceStub = sinon.createStubInstance<RewardDetailService>(RewardDetailService);
      rewardDetailServiceStub.retrieve.resolves({ headers: {} });

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
          rewardDetailService: () => rewardDetailServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rewardDetailServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(RewardDetail, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(rewardDetailServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.rewardDetails[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: RewardDetailComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(RewardDetail, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        rewardDetailServiceStub.retrieve.reset();
        rewardDetailServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        rewardDetailServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeRewardDetail();
        await comp.$nextTick(); // clear components

        // THEN
        expect(rewardDetailServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(rewardDetailServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});

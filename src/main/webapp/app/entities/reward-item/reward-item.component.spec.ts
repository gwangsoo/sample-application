/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import RewardItem from './reward-item.vue';
import RewardItemService from './reward-item.service';
import AlertService from '@/shared/alert/alert.service';

type RewardItemComponentType = InstanceType<typeof RewardItem>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('RewardItem Management Component', () => {
    let rewardItemServiceStub: SinonStubbedInstance<RewardItemService>;
    let mountOptions: MountingOptions<RewardItemComponentType>['global'];

    beforeEach(() => {
      rewardItemServiceStub = sinon.createStubInstance<RewardItemService>(RewardItemService);
      rewardItemServiceStub.retrieve.resolves({ headers: {} });

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
          rewardItemService: () => rewardItemServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rewardItemServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(RewardItem, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(rewardItemServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.rewardItems[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: RewardItemComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(RewardItem, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        rewardItemServiceStub.retrieve.reset();
        rewardItemServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        rewardItemServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeRewardItem();
        await comp.$nextTick(); // clear components

        // THEN
        expect(rewardItemServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(rewardItemServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
